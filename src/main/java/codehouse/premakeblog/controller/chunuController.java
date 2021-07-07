package codehouse.premakeblog.controller;

import codehouse.premakeblog.Service.ChunuService;
import codehouse.premakeblog.dto.PageRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chunu")
@Log4j2
@RequiredArgsConstructor
public class chunuController {

    private final ChunuService service;

    @GetMapping("/")
    public String index() {

        return "redirect:/chunu/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("List: preMakeBlog Main.............");
        log.info(pageRequestDTO);

        model.addAttribute("result", service.getList(pageRequestDTO));
    }
}
