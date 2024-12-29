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
import pmk.artamonova.rgr.model.History;
import pmk.artamonova.rgr.service.HistoryService;
import pmk.artamonova.rgr.service.SubjectService;

import java.util.UUID;

@Controller
@RequestMapping("/histories")
@AllArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    private final SubjectService subjectService;

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("histories", historyService.findAll());
        return "history/all";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("history", new History());
        model.addAttribute("subjects", subjectService.findAll());
        return "history/create";
    }

    @PostMapping
    public String create(@ModelAttribute History history) {
        historyService.create(history);
        return "redirect:/histories/all";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable UUID id, Model model) {
        model.addAttribute(historyService.findById(id));
        return "history/current";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id) {
        historyService.delete(id);
        return "redirect:/histories/all";
    }

    @GetMapping("/{id}/update")
    public String update(@PathVariable("id") UUID id, Model model) {
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("history", historyService.findById(id));
        return "history/update";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute History history, @PathVariable UUID id) {
        historyService.update(history);
        return "redirect:/histories/all";
    }
}
