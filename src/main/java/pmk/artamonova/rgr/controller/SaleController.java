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
import pmk.artamonova.rgr.model.Sale;
import pmk.artamonova.rgr.service.AuctionService;
import pmk.artamonova.rgr.service.OwnerService;
import pmk.artamonova.rgr.service.SaleService;
import pmk.artamonova.rgr.service.SubjectService;

import java.util.UUID;

@Controller
@RequestMapping("/sales")
@AllArgsConstructor
public class SaleController {

    private final SaleService saleService;

    private final AuctionService auctionService;

    private final OwnerService ownerService;

    private final SubjectService subjectService;


    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("sales", saleService.findAll());
        return "sale/all";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("sales", new Sale());
        model.addAttribute("auctions", auctionService.findAll());
        model.addAttribute("owners", ownerService.findAll());
        model.addAttribute("buyers", ownerService.findAll());
        model.addAttribute("subjects", subjectService.findAll());
        return "sale/create";
    }

    @PostMapping
    public String create(@ModelAttribute Sale sale) {
        saleService.create(sale);
        return "redirect:/sales/all";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable UUID id, Model model) {
        model.addAttribute("sale", saleService.findById(id));
        return "sale/current";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id) {
        saleService.delete(id);
        return "redirect:/sales/all";
    }

    @GetMapping("/{id}/update")
    public String update(@PathVariable UUID id, Model model) {
        model.addAttribute("sale", saleService.findById(id));
        model.addAttribute("auctions", auctionService.findAll());
        model.addAttribute("owners", ownerService.findAll());
        model.addAttribute("buyers", ownerService.findAll());
        model.addAttribute("subjects", subjectService.findAll());
        return "sale/update";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute Sale sale, @PathVariable UUID id) {
        saleService.update(sale);
        return "redirect:/sales/all";
    }



}
