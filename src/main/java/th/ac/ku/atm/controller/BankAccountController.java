package th.ac.ku.atm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import th.ac.ku.atm.model.BankAccount;
import th.ac.ku.atm.service.BankAccountService;

@Controller
@RequestMapping("/bankaccount")
public class BankAccountController {

    private BankAccountService accountService;

    public BankAccountController(BankAccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public String getBankAccountPage(Model model) {
        model.addAttribute("bankaccounts", accountService.getBankAccounts());
        return "bankaccount";
    }

    @PostMapping
    public String openAccount(@ModelAttribute BankAccount bankAccount, Model model) {
        accountService.openAccount(bankAccount);
        model.addAttribute("bankaccounts",accountService.getBankAccounts());
        return "redirect:bankaccount";
    }

    @GetMapping("/edit/{id}")
    public String getEditBankAccountPage(@PathVariable int id, Model model) {
        BankAccount account = accountService.getBankAccount(id);
        model.addAttribute("bankAccount", account);
        return "bankaccount-edit";
    }

    @PostMapping("/edit/{id}")
    public String withdrawDepositAccount(@PathVariable int id,
                                         @ModelAttribute BankAccount bankAccount,
                                         Model model, double amount, String btn) {
        BankAccount record = accountService.getBankAccount(bankAccount.getId());
        if (btn.equals("deposit"))
            record.setBalance(record.getBalance()+amount);
        else if (btn.equals("withdraw"))
            record.setBalance(record.getBalance()-amount);
        accountService.editBankAccount(record);

        model.addAttribute("bankaccounts",accountService.getBankAccountList());
        return "redirect:/bankaccount";
//        accountService.editBankAccount(bankAccount);
//        model.addAttribute("bankaccounts",accountService.getBankAccounts());
//        return "redirect:/bankaccount";
    }

    @PostMapping("/delete/{id}")
    public String deleteAccount(@PathVariable int id, @ModelAttribute BankAccount bankAccount, Model model) {
        accountService.deleteBankAccount(bankAccount);
        model.addAttribute("bankAccount", accountService.getBankAccountList());
        return "redirect:/bankaccount";
    }
}
