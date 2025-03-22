package edu.eci.cvds.labReserves.collections;

import edu.eci.cvds.labReserves.model.Laboratory;
import edu.eci.cvds.labReserves.model.Resource;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import edu.eci.cvds.labReserves.model.ScheduleReference;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.annotation.Id;



/**
 * LaboratoryMongodb class specifically for MongoDB persistence.
 * This class maps to the "laboratories" collection in MongoDB.
 */
@Document(collection = "laboratory")
public class LaboratoryMongodb extends Laboratory {

    @Id
    private String id;

    public LaboratoryMongodb(){
        super();
    }

    /**
     * Constructs a LaboratoryMongodb instance based on a Schedule object.
     */
    public LaboratoryMongodb(Laboratory laboratory) {
        super(laboratory.getName(), laboratory.getAbbreviation(), laboratory.getTotalCapacity(), laboratory.getLocation(), laboratory.getScheduleReferences());
    }

    /**
     * Gets the id time of the laboratory.
     * @return The id
     */
    public String getId(){
        return id;
    }
}

