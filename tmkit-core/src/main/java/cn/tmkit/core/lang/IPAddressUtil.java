package cn.tmkit.core.lang;

import java.net.URL;
import java.util.Arrays;

/**
 * IP地址工具类，实现来源于{@code sun.net.util.IPAddressUtil}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-01-28
 */
public class IPAddressUtil {

    private final static int INADDR4SZ = 4;
    private final static int INADDR16SZ = 16;
    private final static int INT16SZ = 2;

    /*
     * Converts IPv4 address in its textual presentation form
     * into its numeric binary form.
     *
     * @param src a String representing an IPv4 address in standard format
     * @return a byte array representing the IPv4 numeric address
     */
    @SuppressWarnings("fallthrough")
    private static byte[] textToNumericFormatV4(String src)
    {
        byte[] res = new byte[INADDR4SZ];

        long tmpValue = 0;
        int currByte = 0;
        boolean newOctet = true;

        int len = src.length();
        if (len == 0 || len > 15) {
            return null;
        }
        /*
         * When only one part is given, the value is stored directly in
         * the network address without any byte rearrangement.
         *
         * When a two part address is supplied, the last part is
         * interpreted as a 24-bit quantity and placed in the right
         * most three bytes of the network address. This makes the
         * two part address format convenient for specifying Class A
         * network addresses as net.host.
         *
         * When a three part address is specified, the last part is
         * interpreted as a 16-bit quantity and placed in the right
         * most two bytes of the network address. This makes the
         * three part address format convenient for specifying
         * Class B net- work addresses as 128.net.host.
         *
         * When four parts are specified, each is interpreted as a
         * byte of data and assigned, from left to right, to the
         * four bytes of an IPv4 address.
         *
         * We determine and parse the leading parts, if any, as single
         * byte values in one pass directly into the resulting byte[],
         * then the remainder is treated as a 8-to-32-bit entity and
         * translated into the remaining bytes in the array.
         */
        for (int i = 0; i < len; i++) {
            char c = src.charAt(i);
            if (c == '.') {
                if (newOctet || tmpValue < 0 || tmpValue > 0xff || currByte == 3) {
                    return null;
                }
                res[currByte++] = (byte) (tmpValue & 0xff);
                tmpValue = 0;
                newOctet = true;
            } else {
                int digit = Character.digit(c, 10);
                if (digit < 0) {
                    return null;
                }
                tmpValue *= 10;
                tmpValue += digit;
                newOctet = false;
            }
        }
        if (newOctet || tmpValue < 0 || tmpValue >= (1L << ((4 - currByte) * 8))) {
            return null;
        }
        switch (currByte) {
            case 0:
                res[0] = (byte) ((tmpValue >> 24) & 0xff);
            case 1:
                res[1] = (byte) ((tmpValue >> 16) & 0xff);
            case 2:
                res[2] = (byte) ((tmpValue >>  8) & 0xff);
            case 3:
                res[3] = (byte) ((tmpValue >>  0) & 0xff);
        }
        return res;
    }

    /*
     * Convert IPv6 presentation level address to network order binary form.
     * credit:
     *  Converted from C code from Solaris 8 (inet_pton)
     *
     * Any component of the string following a per-cent % is ignored.
     *
     * @param src a String representing an IPv6 address in textual format
     * @return a byte array representing the IPv6 numeric address
     */
    private static byte[] textToNumericFormatV6(String src)
    {
        // Shortest valid string is "::", hence at least 2 chars
        if (src.length() < 2) {
            return null;
        }

        int colonp;
        char ch;
        boolean saw_xdigit;
        int val;
        char[] srcb = src.toCharArray();
        byte[] dst = new byte[INADDR16SZ];

        int srcb_length = srcb.length;
        int pc = src.indexOf ("%");
        if (pc == srcb_length -1) {
            return null;
        }

        if (pc != -1) {
            srcb_length = pc;
        }

        colonp = -1;
        int i = 0, j = 0;
        /* Leading :: requires some special handling. */
        if (srcb[i] == ':')
            if (srcb[++i] != ':')
                return null;
        int curtok = i;
        saw_xdigit = false;
        val = 0;
        while (i < srcb_length) {
            ch = srcb[i++];
            int chval = Character.digit(ch, 16);
            if (chval != -1) {
                val <<= 4;
                val |= chval;
                if (val > 0xffff)
                    return null;
                saw_xdigit = true;
                continue;
            }
            if (ch == ':') {
                curtok = i;
                if (!saw_xdigit) {
                    if (colonp != -1)
                        return null;
                    colonp = j;
                    continue;
                } else if (i == srcb_length) {
                    return null;
                }
                if (j + INT16SZ > INADDR16SZ)
                    return null;
                dst[j++] = (byte) ((val >> 8) & 0xff);
                dst[j++] = (byte) (val & 0xff);
                saw_xdigit = false;
                val = 0;
                continue;
            }
            if (ch == '.' && ((j + INADDR4SZ) <= INADDR16SZ)) {
                String ia4 = src.substring(curtok, srcb_length);
                /* check this IPv4 address has 3 dots, ie. A.B.C.D */
                int dot_count = 0, index=0;
                while ((index = ia4.indexOf ('.', index)) != -1) {
                    dot_count ++;
                    index ++;
                }
                if (dot_count != 3) {
                    return null;
                }
                byte[] v4addr = textToNumericFormatV4(ia4);
                if (v4addr == null) {
                    return null;
                }
                for (int k = 0; k < INADDR4SZ; k++) {
                    dst[j++] = v4addr[k];
                }
                saw_xdigit = false;
                break;  /* '\0' was seen by inet_pton4(). */
            }
            return null;
        }
        if (saw_xdigit) {
            if (j + INT16SZ > INADDR16SZ)
                return null;
            dst[j++] = (byte) ((val >> 8) & 0xff);
            dst[j++] = (byte) (val & 0xff);
        }

        if (colonp != -1) {
            int n = j - colonp;

            if (j == INADDR16SZ)
                return null;
            for (i = 1; i <= n; i++) {
                dst[INADDR16SZ - i] = dst[colonp + n - i];
                dst[colonp + n - i] = 0;
            }
            j = INADDR16SZ;
        }
        if (j != INADDR16SZ)
            return null;
        byte[] newdst = convertFromIPv4MappedAddress(dst);
        if (newdst != null) {
            return newdst;
        } else {
            return dst;
        }
    }

    /**
     * @param src a String representing an IPv4 address in textual format
     * @return a boolean indicating whether src is an IPv4 literal address
     */
    private static boolean isIPv4LiteralAddress(String src) {
        return textToNumericFormatV4(src) != null;
    }

    /**
     * @param src a String representing an IPv6 address in textual format
     * @return a boolean indicating whether src is an IPv6 literal address
     */
    private static boolean isIPv6LiteralAddress(String src) {
        return textToNumericFormatV6(src) != null;
    }

    /*
     * Convert IPv4-Mapped address to IPv4 address. Both input and
     * returned value are in network order binary form.
     *
     * @param src a String representing an IPv4-Mapped address in textual format
     * @return a byte array representing the IPv4 numeric address
     */
    private static byte[] convertFromIPv4MappedAddress(byte[] addr) {
        if (isIPv4MappedAddress(addr)) {
            byte[] newAddr = new byte[INADDR4SZ];
            System.arraycopy(addr, 12, newAddr, 0, INADDR4SZ);
            return newAddr;
        }
        return null;
    }

    /**
     * Utility routine to check if the InetAddress is an
     * IPv4 mapped IPv6 address.
     *
     * @return a <code>boolean</code> indicating if the InetAddress is
     * an IPv4 mapped IPv6 address; or false if address is IPv4 address.
     */
    private static boolean isIPv4MappedAddress(byte[] addr) {
        if (addr.length < INADDR16SZ) {
            return false;
        }
        if ((addr[0] == 0x00) && (addr[1] == 0x00) &&
                (addr[2] == 0x00) && (addr[3] == 0x00) &&
                (addr[4] == 0x00) && (addr[5] == 0x00) &&
                (addr[6] == 0x00) && (addr[7] == 0x00) &&
                (addr[8] == 0x00) && (addr[9] == 0x00) &&
                (addr[10] == (byte)0xff) &&
                (addr[11] == (byte)0xff))  {
            return true;
        }
        return false;
    }

    // See java.net.URI for more details on how to generate these
    // masks.
    //
    // square brackets
    private static final long L_IPV6_DELIMS = 0x0L; // "[]"
    private static final long H_IPV6_DELIMS = 0x28000000L; // "[]"
    // RFC 3986 gen-delims
    private static final long L_GEN_DELIMS = 0x8400800800000000L; // ":/?#[]@"
    private static final long H_GEN_DELIMS = 0x28000001L; // ":/?#[]@"
    // These gen-delims can appear in authority
    private static final long L_AUTH_DELIMS = 0x400000000000000L; // "@[]:"
    private static final long H_AUTH_DELIMS = 0x28000001L; // "@[]:"
    // colon is allowed in userinfo
    private static final long L_COLON = 0x400000000000000L; // ":"
    private static final long H_COLON = 0x0L; // ":"
    // slash should be encoded in authority
    private static final long L_SLASH = 0x800000000000L; // "/"
    private static final long H_SLASH = 0x0L; // "/"
    // backslash should always be encoded
    private static final long L_BACKSLASH = 0x0L; // "\"
    private static final long H_BACKSLASH = 0x10000000L; // "\"
    // ASCII chars 0-31 + 127 - various controls + CRLF + TAB
    private static final long L_NON_PRINTABLE = 0xffffffffL;
    private static final long H_NON_PRINTABLE = 0x8000000000000000L;
    // All of the above
    private static final long L_EXCLUDE = 0x84008008ffffffffL;
    private static final long H_EXCLUDE = 0x8000000038000001L;

    private static final char[] OTHERS = {
            8263,8264,8265,8448,8449,8453,8454,10868,
            65109,65110,65119,65131,65283,65295,65306,65311,65312
    };

    // Tell whether the given character is found by the given mask pair
    public static boolean match(char c, long lowMask, long highMask) {
        if (c < 64)
            return ((1L << c) & lowMask) != 0;
        if (c < 128)
            return ((1L << (c - 64)) & highMask) != 0;
        return false; // other non ASCII characters are not filtered
    }

    // returns -1 if the string doesn't contain any characters
    // from the mask, the index of the first such character found
    // otherwise.
    public static int scan(String s, long lowMask, long highMask) {
        int i = -1, len;
        if (s == null || (len = s.length()) == 0) return -1;
        boolean match = false;
        while (++i < len && !(match = match(s.charAt(i), lowMask, highMask)));
        if (match) return i;
        return -1;
    }

    public static int scan(String s, long lowMask, long highMask, char[] others) {
        int i = -1, len;
        if (s == null || (len = s.length()) == 0) return -1;
        boolean match = false;
        char c, c0 = others[0];
        while (++i < len && !(match = match((c=s.charAt(i)), lowMask, highMask))) {
            if (c >= c0 && (Arrays.binarySearch(others, c) > -1)) {
                match = true; break;
            }
        }
        if (match) return i;

        return -1;
    }

    private static String describeChar(char c) {
        if (c < 32 || c == 127) {
            if (c == '\n') return "LF";
            if (c == '\r') return "CR";
            return "control char (code=" + (int)c + ")";
        }
        if (c == '\\') return "'\\'";
        return "'" + c + "'";
    }

    private static String checkUserInfo(String str) {
        // colon is permitted in user info
        int index = scan(str, L_EXCLUDE & ~L_COLON,
                H_EXCLUDE & ~H_COLON);
        if (index >= 0) {
            return "Illegal character found in user-info: "
                    + describeChar(str.charAt(index));
        }
        return null;
    }

    private static String checkHost(String str) {
        int index;
        if (str.startsWith("[") && str.endsWith("]")) {
            str = str.substring(1, str.length() - 1);
            if (isIPv6LiteralAddress(str)) {
                index = str.indexOf('%');
                if (index >= 0) {
                    index = scan(str = str.substring(index),
                            L_NON_PRINTABLE | L_IPV6_DELIMS,
                            H_NON_PRINTABLE | H_IPV6_DELIMS);
                    if (index >= 0) {
                        return "Illegal character found in IPv6 scoped address: "
                                + describeChar(str.charAt(index));
                    }
                }
                return null;
            }
            return "Unrecognized IPv6 address format";
        } else {
            index = scan(str, L_EXCLUDE, H_EXCLUDE);
            if (index >= 0) {
                return "Illegal character found in host: "
                        + describeChar(str.charAt(index));
            }
        }
        return null;
    }

    private static String checkAuth(String str) {
        int index = scan(str,
                L_EXCLUDE & ~L_AUTH_DELIMS,
                H_EXCLUDE & ~H_AUTH_DELIMS);
        if (index >= 0) {
            return "Illegal character found in authority: "
                    + describeChar(str.charAt(index));
        }
        return null;
    }

    // check authority of hierarchical URL. Appropriate for
    // HTTP-like protocol handlers
    public static String checkAuthority(URL url) {
        String s, u, h;
        if (url == null) return null;
        if ((s = checkUserInfo(u = url.getUserInfo())) != null) {
            return s;
        }
        if ((s = checkHost(h = url.getHost())) != null) {
            return s;
        }
        if (h == null && u == null) {
            return checkAuth(url.getAuthority());
        }
        return null;
    }

    // minimal syntax checks - deeper check may be performed
    // by the appropriate protocol handler
    public static String checkExternalForm(URL url) {
        String s;
        if (url == null) return null;
        int index = scan(s = url.getUserInfo(),
                L_NON_PRINTABLE | L_SLASH,
                H_NON_PRINTABLE | H_SLASH);
        if (index >= 0) {
            return "Illegal character found in authority: "
                    + describeChar(s.charAt(index));
        }
        if ((s = checkHostString(url.getHost())) != null) {
            return s;
        }
        return null;
    }

    public static String checkHostString(String host) {
        if (host == null) return null;
        int index = scan(host,
                L_NON_PRINTABLE | L_SLASH,
                H_NON_PRINTABLE | H_SLASH,
                OTHERS);
        if (index >= 0) {
            return "Illegal character found in host: "
                    + describeChar(host.charAt(index));
        }
        return null;
    }

    /**
     * 判断是否为IPV4
     *
     * @param cse 待检测的字符串
     * @return 是否为IPv4
     */
    public static boolean isIPv4(CharSequence cse) {
        if (StrUtil.isBlank(cse)) {
            return false;
        }
        String str = cse.toString();
        // 做一层简单的正则校验，不再支持老旧的简化写法，比如1代表的是0.0.0.1
        String regex = "^\\d{1,3}\\.\\d{1,3}.\\d{1,3}.\\d{1,3}$";
        if (!RegexUtil.isMatch(regex, cse)) {
            return false;
        }
        return isIPv4LiteralAddress(str);
    }

    /**
     * 判断是否为IPV6
     *
     * @param cse 待检测的字符串
     * @return 是否为IPv6
     */
    public static boolean isIPv6(CharSequence cse) {
        if (StrUtil.isBlank(cse)) {
            return false;
        }
        String str = cse.toString();
        return isIPv6LiteralAddress(str);
    }

}
