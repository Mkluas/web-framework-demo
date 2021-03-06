package cn.mklaus.demo;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @author Mklaus
 * @date 2018-03-28 下午4:10
 */
@SpringBootApplication
public class App extends SpringBootServletInitializer {

    // 打包成 war 时， 必须继承 SpringBootServletInitializer.

    public static void main(String[] args) {
        new SpringApplicationBuilder(App.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }

}
