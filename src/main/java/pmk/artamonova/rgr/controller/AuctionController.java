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
import pmk.artamonova.rgr.model.Auction;
import pmk.artamonova.rgr.service.AuctionService;

import java.util.UUID;

@Controller()
@RequestMapping("/auctions")
@AllArgsConstructor
public class AuctionController {

    private final AuctionService service;

    @GetMapping("/all")
    public String hui(Model model) {
        model.addAttribute("auctions", service.findAll());
        return "auction/all";
    }

    @GetMapping("/create")
    public String newAuction(Model model) {
        model.addAttribute("auction", new Auction());
        return "auction/create";
    }

    @PostMapping
    public String create(@ModelAttribute("auction") Auction auction) {
        service.create(auction);
        return "redirect:/auctions/all";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable UUID id, Model model) {
        model.addAttribute("auction", service.findById(id));
        return "auction/current";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id) {
        service.delete(id);
        return "redirect:/auctions/all";
    }

    @GetMapping("/{id}/update")
    public String show(@PathVariable UUID id, Model model) {
        model.addAttribute("auction", service.findById(id));
        return "auction/update";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("auction") Auction auction, @PathVariable UUID id) {
        service.update(auction);
        return "redirect:/auctions/all";
    }
}
