/* ClientServiceImpl.java
   Author: S Hendricks (221095136)
   Date: 09 July 2025 */

package za.co.hireahelper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.repository.ClientRepository;
import za.co.hireahelper.service.ClientService;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository repository;

    @Autowired
    public ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Client create(Client client) {
        return this.repository.save(client);
    }

    @Override
    public Client read(String id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public Client update(Client client) {
        return this.repository.save(client);
    }

    @Override
    public boolean delete(String id) {
        if (this.repository.existsById(id)) {
            this.repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Client> getAll() {
        return this.repository.findAll();
    }
}