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
import pmk.artamonova.rgr.model.Event;
import pmk.artamonova.rgr.service.EventService;
import pmk.artamonova.rgr.service.HistoryService;

import java.util.UUID;

@Controller
@RequestMapping("/events")
@AllArgsConstructor
public class EventController {

    private final EventService eventService;

    private final HistoryService historyService;

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("events", eventService.findAll());
        return "event/all";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("histories", historyService.findAll());
        return "event/create";
    }

    @PostMapping
    public String create(@ModelAttribute("event") Event event) {
        eventService.create(event);
        return "redirect:/events/all";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable UUID id, Model model) {
        model.addAttribute("event", eventService.findById(id));
        return "event/current";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id) {
        eventService.delete(id);
        return "redirect:/events/all";
    }

    @GetMapping("/{id}/update")
    public String update(@PathVariable UUID id, Model model) {
        model.addAttribute("event", eventService.findById(id));
        model.addAttribute("histories", historyService.findAll());
        return "event/update";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("event") Event event, @PathVariable UUID id) {
        eventService.update(event);
        return "redirect:/events/all";
    }
}
