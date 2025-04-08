package Customer;

import java.sql.SQLException;
import java.util.ArrayList;

//In the customerService we add all the methods that we want to use, getallcustomers, updatecustomerbyid and so on
public class CustomerService {
    CustomerRepository customerRepository = new CustomerRepository();

    public ArrayList<Customer> getAllCustomers() throws SQLException {

        return customerRepository.getAll();
    }

    public Customer getCustomerById(int id) throws SQLException {

        return customerRepository.getCustomerById(id);
    }

    public Customer updateCustomerEmail(String email, int id) throws SQLException {
        return customerRepository.updateCustomersEmail(email, id);
    }

    public Customer addCustomer(Customer customer) throws SQLException {
        return customerRepository.AddCustomer(customer);
    }
}
