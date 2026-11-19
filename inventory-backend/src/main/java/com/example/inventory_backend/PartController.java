package com.example.inventory_backend;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class PartController{

    private final PartRepository partRepository;

    public PartController(PartRepository partRepository){
        this.partRepository = partRepository;
    }

    
   
   @GetMapping("/api/hello")
    public String hello(){
        return "Inventory backend is working!";
    }
    @GetMapping("/api/part")
    public Part getPart(){
        return new Part("Oil filter", 4);
    }
    @GetMapping("/api/parts")
    public List<PartEntity> getParts(){
         return partRepository.findAll();
    }
    @PostMapping("/api/parts")
    public PartEntity addPart(@RequestBody Part part){
        if (part.name() == null || part.name().isBlank() || part.quantity() < 0){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Enter a name and a quantity of 0 or more."
            );
        }
        PartEntity newPart = new PartEntity(
            part.name().trim(), part.quantity()
        );
        return partRepository.save(newPart);
    }
    @PostMapping("/api/parts/{id}/use")
    public PartEntity usePart(@PathVariable("id") Long id){
        int updatedRows = partRepository.useOne(id);

        if (updatedRows == 0){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Part not found or out of stock."
            );
        }
        return partRepository.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Part not found.")
        );
    }
    
}
