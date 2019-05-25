package com.ceiba.laboratorio.library.controller;

import com.ceiba.laboratorio.library.entity.Book;
import com.ceiba.laboratorio.library.exception.ResourceException;
import com.ceiba.laboratorio.library.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private IBookRepository bookRepository;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/{isbm}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Book> findBook(@PathVariable Long isbm){
        Optional<Book> product =  bookRepository.findById(isbm);
        return  product;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void save(@Valid @RequestBody Book book){
        bookRepository.save(book);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Book update(@PathVariable Long id, @Valid @RequestBody Book bookRequest){
        return bookRepository.findById(id).map(bookTemp -> {
            bookTemp.setIsbm(bookRequest.getIsbm());
            bookTemp.setDescription(bookRequest.getDescription());
            bookTemp.setTitle(bookRequest.getTitle());
            bookTemp.setCreated(bookRequest.getCreated());
            bookTemp.setAuthor(bookRequest.getAuthor());
            return bookRepository.save(bookTemp);
        }).orElseThrow(()-> new ResourceException("Libro " + id + " not found"));
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id){
        bookRepository.deleteById(id);
    }
}


