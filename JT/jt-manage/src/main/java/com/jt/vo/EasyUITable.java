package com.jt.vo;

import com.jt.pojo.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true) //链式加载规则
@NoArgsConstructor
@AllArgsConstructor
// 当服务之间字节数组(对象)传递时,必须实现序列化接口.
// json的本质就是字符串可以直接传递.
public class EasyUITable  implements Serializable {
    private Integer total;
    private List<Item> rows;
}
