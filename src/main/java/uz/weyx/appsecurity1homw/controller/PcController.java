package uz.weyx.appsecurity1homw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.weyx.appsecurity1homw.entity.Pc;
import uz.weyx.appsecurity1homw.repository.PcRepository;

import java.util.Optional;

@RestController
@RequestMapping("/pcmarket.uz")
public class PcController {

    @Autowired
    PcRepository pcRepository;

    //for all product
    @PreAuthorize(value = "hasAnyRole('USER','MODERATOR','SUPER_ADMIN')")
    @GetMapping
    public HttpEntity<?> get(){
        return ResponseEntity.ok(pcRepository.findAll());
    }

    //for one product
    @PreAuthorize(value = "hasAnyRole('USER','MODERATOR','SUPER_ADMIN')")
    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id){
        Optional<Pc> optionalPc = pcRepository.findById(id);
        if(optionalPc.isPresent())
            return ResponseEntity.ok(optionalPc.get());
        return ResponseEntity.notFound().build();
    }

    //for add product
    @PreAuthorize(value = "hasAnyRole('MODERATOR','SUPER_ADMIN')")
    @PostMapping
    public HttpEntity<?> add(@RequestBody Pc pc){
        return ResponseEntity.ok(pcRepository.save(pc));
    }

    //for delete product
    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        Optional<Pc> optionalPc = pcRepository.findById(id);
        if(optionalPc.isPresent()) {
            pcRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //for edit prodcut
    @PreAuthorize(value = "hasAnyRole('MODERATOR','SUPER_ADMIN')")
    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id,@RequestBody Pc pc){
        Optional<Pc> optionalPc = pcRepository.findById(id);
        if(optionalPc.isPresent()){
            Pc editedPc = optionalPc.get();
            editedPc.setModel(pc.getModel());
            editedPc.setPrice(pc.getPrice());
            pcRepository.save(editedPc);
            String success="edited";
            return ResponseEntity.ok(success);
        }
        return ResponseEntity.notFound().build();
    }
}
