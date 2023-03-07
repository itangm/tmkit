package cn.tmkit.test.apiserver.vo;

import lombok.*;

import java.io.Serializable;

/**
 * 文件的信息
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-06
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileData implements Serializable {

    private String oldFilename;

    private String newFilename;

    private int fileSize;

    private String fileExt;

    private String showUrl;

}
