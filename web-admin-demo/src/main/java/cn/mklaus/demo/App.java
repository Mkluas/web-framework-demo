package cn.mklaus.demo;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Mklaus
 * @date 2018-03-28 下午4:10
 */
@EnableAsync
@SpringBootApplication
public class App extends SpringBootServletInitializer {

    public static void main(String[] args) {
        new SpringApplicationBuilder(App.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }

}
