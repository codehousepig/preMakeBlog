package codehouse.premakeblog.controller;

import codehouse.premakeblog.Service.ChunuService;
import codehouse.premakeblog.dto.ChunuDTO;
import codehouse.premakeblog.dto.PageRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/register")
    public void register(){
        log.info("register get...");
    }

    @PostMapping("/register")
    public String registerPost(ChunuDTO dto, RedirectAttributes redirectAttributes){
        log.info("Cont_PostRegister; dto: " + dto);
        Long cno = service.register(dto);
        redirectAttributes.addFlashAttribute("msg", cno);

        return "redirect:/chunu/list";
    }

    // @GetMapping("/read")
    @GetMapping({"/read", "/modify"})
    public void read(Long cno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        log.info("cno: Read..." + cno);
        ChunuDTO dto = service.read(cno);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/remove")
    public String remove(Long cno, RedirectAttributes redirectAttributes){
        log.info("Remove cno: ..." + cno);
        service.remove(cno);
        redirectAttributes.addFlashAttribute("msg", cno);

        return "redirect:/chunu/list";
    }

    @PostMapping("/modify")
    public String modify(ChunuDTO dto, RedirectAttributes redirectAttributes,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO){
        log.info("modify Post: .....");
        log.info("dto: " + dto);

        service.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("cno", dto.getCno());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getPage());
        return "redirect:/chunu/read";
    }


}
