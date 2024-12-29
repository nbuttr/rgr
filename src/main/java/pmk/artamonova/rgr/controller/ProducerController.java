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
import pmk.artamonova.rgr.model.Producer;
import pmk.artamonova.rgr.service.ProducerService;

import java.util.List;
import java.util.UUID;

@Controller()
@RequestMapping("/producers")
@AllArgsConstructor
public class ProducerController {

    private final ProducerService producerService;

    @GetMapping("/all")
    public String all(Model model) {
        List<Producer> producerList = producerService.findAll();
        model.addAttribute("producers",producerList);
        return "producer/all";
    }
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("producer", new Producer());
        return "producer/create";
    }
    @PostMapping
    public String create(@ModelAttribute("producer") Producer producer) {
        producerService.create(producer);
        return "redirect:/producers/all";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable UUID id, Model model) {
        model.addAttribute("producer",producerService.findById(id));
        return "producer/current";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") UUID id) {
        producerService.delete(id);
        return "redirect:/producers/all";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("producer") Producer producer, @PathVariable UUID id) {
        producerService.update(producer);
        return "redirect:/producers/all";
    }

    @GetMapping("/{id}/update")
    public String update(@PathVariable("id") UUID id, Model model) {
        model.addAttribute("producer",producerService.findById(id));
        return "producer/update";
    }
}
