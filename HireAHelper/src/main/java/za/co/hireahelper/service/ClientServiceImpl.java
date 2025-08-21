/* ClientServiceImpl.java
   Author: S Hendricks (221095136)
   Date: 09 July 2025
*/

package za.co.hireahelper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.repository.ClientRepository;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public ClientServiceImpl(ClientRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Client create(Client client) {
        // Hash password before saving
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        return this.repository.save(client);
    }

    @Override
    public Client read(String id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public Client update(Client client) {
        // Optionally hash password if it's being updated
        if (client.getPassword() != null) {
            client.setPassword(passwordEncoder.encode(client.getPassword()));
        }
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
