package th.ac.ku.atm.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import th.ac.ku.atm.model.BankAccount;
import th.ac.ku.atm.model.Customer;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BankAccountService {
    private List<BankAccount> bankAccountList;

    private RestTemplate restTemplate;

    public BankAccountService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void openAccount(BankAccount bankAccount) {
        String url = "http://localhost:8091/api/bankaccount";

        restTemplate.postForObject(url, bankAccount, BankAccount.class);
    }


    public List<BankAccount> getCustomerBankAccounts(int customerId) {
        String url = "http://localhost:8091/api/bankaccount/customer/" +
                customerId;
        ResponseEntity<BankAccount[]> response =
                restTemplate.getForEntity(url, BankAccount[].class);

        BankAccount[] accounts = response.getBody();

        return Arrays.asList(accounts);
    }

    public BankAccount getBankAccount(int id) {
        String url = "http://localhost:8091/api/bankaccount/" + id;

        ResponseEntity<BankAccount> response =
                restTemplate.getForEntity(url, BankAccount.class);

        return response.getBody();
    }

    public void editBankAccount(BankAccount bankAccount) {
        String url = "http://localhost:8091/api/bankaccount/" +
                bankAccount.getId();
        restTemplate.put(url, bankAccount);
    }


    public List<BankAccount> getBankAccounts() {
        String url = "http://localhost:8091/api/bankaccount";
        ResponseEntity<BankAccount[]> response = restTemplate.getForEntity(url, BankAccount[].class);
        BankAccount[] accounts = response.getBody();

        return Arrays.asList(accounts);
    }
    public void deleteBankAccount(BankAccount bankAccount) {
        String url = "http://localhost:8091/api/bankaccount/" + bankAccount.getId();

        restTemplate.delete(url);
    }
    @PostConstruct
    public void postConstruct() {
        bankAccountList = new ArrayList<>();
    }

    public void createBankAccount(BankAccount bankAccount) {
        bankAccountList.add(bankAccount);
    }

    public List<BankAccount> getBankAccountList() {
        return new ArrayList<>(bankAccountList);
    }
}
