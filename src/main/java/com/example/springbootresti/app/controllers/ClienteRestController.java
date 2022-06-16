package com.example.springbootresti.app.controllers;

import com.example.springbootresti.app.models.entity.Cliente;
import com.example.springbootresti.app.models.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

//API REST - sino ponemos methods permite todos los tipos de como patch,get,etc
/*@CrossOrigin(origins = {"${app.api.settings.cross-origin.urls}"}
        ,methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})*/
@CrossOrigin(origins = {"https://angular-springboot-restful.herokuapp.com"},
        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
@RestController
@RequestMapping("/api")
public class ClienteRestController {

    @Autowired
    IClienteService clienteService;

    @GetMapping("/clientes")
    public List<Cliente> index(){
        return clienteService.findAll();
    }

    @GetMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.OK) //Esto no es necesario ya que es HttpStatus.OK por defecto
    public ResponseEntity<?> show(@PathVariable Long id){
        Cliente cliente=null;
        Map<String,Object> res=new HashMap<>();
        try {
            cliente=clienteService.findById(id);
        }catch (DataAccessException e){
            res.put("message","Error al realizar la consulta a la base de datos");
            res.put("error", Objects.requireNonNull(e.getMessage()).concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (cliente == null){
            res.put("message","El cliente con el ID: ".concat(id.toString()).concat(" no existe en la base de datos"));
            return new ResponseEntity<>(res,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    /** Se usa el @RequestBody para mapear el cliente le ponemos HttpStatus.CREATED(201) ya que por defecto es HttpStatus.OK(200)**/
    @PostMapping("/clientes")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) {
        System.out.println("39:id-> " + cliente.getId());
        //cliente.setCreateAt(new Date());
        Map<String, Object> res = new HashMap<>();
        if (result.hasErrors()){
            List<String> errors = new ArrayList<>();
            for (FieldError err:result.getFieldErrors()) {
                errors.add("El campo '"+err.getField()+"' "+err.getDefaultMessage());
            }

            return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST); //400
        }

        Cliente clienteOk;
        try {
            clienteOk = clienteService.save(cliente);
        } catch (DataAccessException e) {
            res.put("message", "Error al realizar el insert en la base de datos");
            res.put("error", Objects.requireNonNull(e.getMessage()).concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
        res.put("message", "El cliente ha sido creado con exito !");
        res.put("cliente", clienteOk);
        return new ResponseEntity<>(res,HttpStatus.CREATED); //201
    }


    //@ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente,BindingResult result,
                                    @PathVariable Long id){
        Cliente clienteActual = clienteService.findById(id);
        Cliente clienteUpdate;
        Map<String,Object> res = new HashMap<>();

        if (result.hasErrors()){
            List<String> errorsL = result.getFieldErrors().stream()
                    .map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
                    .collect(Collectors.toList());
            res.put("errorsBR",errorsL);
            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }

        if (clienteActual == null){
            res.put("message","El cliente con el ID: ".concat(id.toString()).concat("no se puede actualizar porque no existe en la base de datos"));
            return new ResponseEntity<>(res,HttpStatus.NOT_FOUND);
        }
        try{
            clienteActual.setNombre(cliente.getNombre());
            clienteActual.setApellido(cliente.getApellido());
            clienteActual.setEmail(cliente.getEmail());
            clienteActual.setCreateAt(cliente.getCreateAt());
            clienteUpdate= clienteService.save(clienteActual);
        }catch (DataAccessException e){
            res.put("message", "Error al actualizar el insert en la base de datos");
            res.put("error", Objects.requireNonNull(e.getMessage()).concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        res.put("mensaje", "El cliente ha sido actualizado con exito !");
        res.put("cliente", clienteUpdate);
        return new ResponseEntity<>(res,HttpStatus.CREATED);
    }

    
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        Map<String,Object> res=new HashMap<>();

        try {
            clienteService.delete(id);
        } catch (DataAccessException e) {
            res.put("message", "Error al realizar el delete en la base de datos!");
            res.put("error", Objects.requireNonNull(e.getMessage()).concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        res.put("mensaje","El cliente ha sido eliminado con exito");
        return new ResponseEntity<>(res,HttpStatus.OK);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/clientes/delAll")
    public List<Cliente> deleteAllClients(){
        clienteService.deleteAll();
        //Algo interesante es que si hacemos un deleteAll y despues un findAll jpa me devuelve una lista vacia pero no null
        return clienteService.findAll();
    }





    /**Este es un metodo para testear las cabeceras de la peticion no se usa en angular y no forma parte
     * de la apiRestFul**/
    @GetMapping("/host")
    public List<Object> getHost(HttpServletRequest request){
       String r1=request.getRemoteHost();
       String r2 = request.getHeader("Host");
        Enumeration<String> enums=request.getHeaderNames();
        List<String> listE=new ArrayList<>();
       HeadersSpring hS= new HeadersSpring();
        while (enums.hasMoreElements()){
            hS.user_agent=hS.user_agent==null?request.getHeader(enums.nextElement()):hS.user_agent;
            hS.accept=hS.accept==null?request.getHeader(enums.nextElement()):hS.accept;
            hS.postman_token=hS.postman_token==null?request.getHeader(enums.nextElement()):hS.postman_token;
            hS.host=hS.host==null?request.getHeader(enums.nextElement()):hS.host;
            hS.accept_encoding=hS.accept_encoding==null?request.getHeader(enums.nextElement()):hS.accept_encoding;
            hS.connection=hS.connection==null?request.getHeader(enums.nextElement()):hS.connection;
            //listE.add(enums.nextElement());
       }

       return List.of(r1,r2,hS);
    }
}
class HeadersSpring {
    public String user_agent;
    public String accept;
    public String postman_token;
    public String host;
    public String accept_encoding;
    public String connection;

}

