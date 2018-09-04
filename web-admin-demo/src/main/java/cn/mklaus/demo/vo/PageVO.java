package cn.mklaus.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.nutz.dao.pager.Pager;

/**
 * @author Klaus
 * @date 2018/7/7
 */
@ApiModel
@Data
@ToString
public class PageVO {

    @ApiModelProperty(name = "搜索关键词, 选填")
    private String key;

    @ApiModelProperty(name = "页码, 默认为1", example = "1")
    private Integer pageNumber;

    @ApiModelProperty(name = "页大小, 默认为20", example = "20")
    private Integer pageSize;

    public PageVO() {
        this.pageNumber = 1;
        this.pageSize = 20;
        this.key = "";
    }

    public Pager toPager() {
        return new Pager(pageNumber, pageSize);
    }

}
