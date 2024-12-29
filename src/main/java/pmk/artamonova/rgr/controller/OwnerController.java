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
import pmk.artamonova.rgr.model.Owner;
import pmk.artamonova.rgr.service.OwnerService;

import java.util.UUID;

@Controller
@RequestMapping("/owners")
@AllArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("owners", ownerService.findAll());
        return "owner/all";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("owner", new Owner());
        return "owner/create";
    }

    @PostMapping
    public String create(@ModelAttribute Owner owner) {
        ownerService.create(owner);
        return "redirect:/owners/all";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable UUID id, Model model) {
        model.addAttribute("owner", ownerService.findById(id));
        return "owner/current";
    }

    @GetMapping("/{id}/update")
    public String update(@PathVariable UUID id, Model model) {
        model.addAttribute("owner", ownerService.findById(id));
        return "owner/update";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable UUID id, Owner owner) {
        ownerService.update(owner);
        return "redirect:/owners/all";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id) {
        ownerService.delete(id);
        return "redirect:/owners/all";
    }
}
