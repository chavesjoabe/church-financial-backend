package com.treasury.treasury.tax.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.treasury.treasury.tax.schema.Tax;

public interface TaxRepository extends MongoRepository<Tax, String>{ }
