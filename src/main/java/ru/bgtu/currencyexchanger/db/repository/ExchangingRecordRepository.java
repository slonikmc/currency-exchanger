package ru.bgtu.currencyexchanger.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bgtu.currencyexchanger.db.model.ExchangingRecord;

import java.util.UUID;

public interface ExchangingRecordRepository extends JpaRepository<ExchangingRecord, UUID> {

}