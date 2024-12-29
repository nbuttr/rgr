package pmk.artamonova.rgr.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pmk.artamonova.rgr.model.Subject;
import pmk.artamonova.rgr.service.OwnerService;
import pmk.artamonova.rgr.service.ProducerService;
import pmk.artamonova.rgr.service.SaleService;
import pmk.artamonova.rgr.service.SubjectService;

import java.util.UUID;

@Controller
@RequestMapping("/subjects")
@AllArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    private final OwnerService ownerService;

    private final ProducerService producerService;

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("subjects", subjectService.findAll());
        return "subject/all";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("subject", new Subject());
        model.addAttribute("producers", producerService.findAll());
        model.addAttribute("owners", ownerService.findAll());
        return "subject/create";
    }

    @PostMapping
    public String create(@ModelAttribute Subject subject) {
        subjectService.create(subject);
        return "redirect:/subjects/all";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable UUID id, Model model) {
        model.addAttribute("subject", subjectService.findById(id));
        return "subject/current";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id) {
        subjectService.delete(id);
        return "redirect:/subjects/all";
    }

    @GetMapping("/{id}/update")
    public String update(@PathVariable UUID id, Model model) {
        model.addAttribute("subject", subjectService.findById(id));
        model.addAttribute("producers", producerService.findAll());
        model.addAttribute("owners", ownerService.findAll());
        return "subject/update";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute Subject subject) {
        subjectService.update(subject);
        return "redirect:/subjects/all";
    }
}
