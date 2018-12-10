package com.dudu.custweb;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author： zhangjianbin <br/>
 * ===============================
 * Created with IDEA.
 * Date： 2018/12/10 18:11
 * ================================
 */
@Getter
@Setter
public class orderModel {

    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime date;

}
