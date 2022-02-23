package hu.yokudlela.haccp.logic;

import hu.yokudlela.haccp.model.StorageControl;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * This class is responsible for handling the data regarding storage management.
 *
 * @author csabakoos
 */
@Service
public class StorageRepository {
    private final List<StorageControl> storageControlList;

    /**
     * The constructor of the class
     */
    public StorageRepository() {
        this.storageControlList = new ArrayList<>();
    }

    /**
     * Adds a new record to the collection of the storage management's control records.
     *
     * @param record The new record to be added.
     * @throws InstanceAlreadyExistsException This is thrown if duplicate record to be added.
     */
    public void addRecord(StorageControl record) throws InstanceAlreadyExistsException {
        if (this.storageControlList.stream().anyMatch(x -> x.getId().equals(record.getId()))) {
            throw new InstanceAlreadyExistsException("Record already exists with the given id");
        } else {
            this.storageControlList.add(record);
        }
    }

    /**
     * Returns a record based on the given id or throws an exception accordingly.
     *
     * @param id The id that is used to search for the desired record.
     * @return The desired record.
     * @throws NoSuchElementException Threw when can't get record since non exists with the given id.
     */
    public StorageControl getRecord(String id) {
        Optional<StorageControl> optional = storageControlList.stream().filter(x -> x.getId().equals(id)).findFirst();
        if (!optional.isEmpty()) {
            return optional.get();
        } else {
            throw new NoSuchElementException(String.format("No record exists with the given id (%s)", id));
        }
    }

    /**
     * Returns a record based on the given id or throws an exception accordingly.
     *
     * @param date The date that is used to search for the desired record.
     * @return The desired record.
     * @throws NoSuchElementException Threw when can't get record since non exists with the given id.
     */
    public StorageControl getRecord(LocalDate date) {
        Optional<StorageControl> optional = storageControlList.stream().filter(x -> x.getDate().equals(date)).findFirst();
        if (!optional.isEmpty()) {
            return optional.get();
        } else {
            throw new NoSuchElementException(String.format("No record exists with the given date (%s)", date));
        }
    }

    /**
     * Removes a record that has the given ID from the collection.
     *
     * @param id The desired records ID to be deleted.
     * @throws NoSuchElementException Threw when can't delete record since non exists with the given id.
     */
    public void deleteRecord(String id) throws NoSuchElementException {
        if (!this.storageControlList.stream().anyMatch(x -> x.getId().equals(id))) {
            throw new NoSuchElementException("No record exists with the given id");
        } else {
            this.storageControlList.removeIf(x -> x.getId().equals(id));
        }
    }
}