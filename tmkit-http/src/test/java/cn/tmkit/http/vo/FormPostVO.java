package cn.tmkit.http.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author miles.tang
 * @version 0.0.1
 * @date 2023-03-07
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
public class FormPostVO extends SimplePostVO {

    private Map<String, List<FileData>> files;

}
