package codehouse.premakeblog.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chunu")
@Log4j2
public class chunuController {

    @GetMapping({"/", "/list"})
    public String list() {
        log.info("List: preMakeBlog Main.............");

        return "/premakeblog/list";
    }
}
