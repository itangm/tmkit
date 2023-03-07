package cn.tmkit.test.apiserver.vo;

import lombok.*;

import java.io.Serializable;

/**
 * 淘宝信息
 *
 * @author ming.tang
 * @version 0.0.1
 * @date 2023-03-06
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class IpApiInfo implements Serializable {

    /**
     * status,success for fail
     */
    private String status;

    /**
     * error message
     */
    private String message;

    /**
     * Country name
     */
    private String country;

    /**
     * Two-letter country code ISO 3166-1 alpha-2
     */
    private String countryCode;

    /**
     * Region/state short code (FIPS or ISO)
     */
    private String region;

    /**
     * Region/state
     */
    private String regionName;

    /**
     * City
     */
    private String city;

    /**
     * District (subdivision of city)
     */
    private String district;

    /**
     * Zip code
     */
    private String zip;

    /**
     * Latitude
     */
    private Float lat;

    /**
     * Longitude
     */
    private Float lon;

    /**
     * Timezone (tz)
     */
    private String timezone;

    /**
     * Timezone UTC DST offset in seconds
     */
    private int offset;

    /**
     * National currency
     */
    private String currency;

    /**
     * ISP name
     */
    private String isp;

    /**
     * Organization name
     */
    private String org;

    /**
     * AS number and organization, separated by space (RIR). Empty for IP blocks not being announced in BGP tables.
     */
    private String as;

    /**
     * IP used for the query
     */
    private String query;

    public static final String SUCCESS = "success";

    public static final String FAILURE = "fail";

    public boolean success() {
        return SUCCESS.equals(status);
    }

}
