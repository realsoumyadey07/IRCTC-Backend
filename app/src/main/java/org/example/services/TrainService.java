package org.example.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.example.entities.Train;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TrainService {
     private List<Train> trains;
     private static final String TRAIN_PATH = "app/src/main/java/org/example/localDb/train.json";
     private ObjectMapper objectMapper = new ObjectMapper();

     
     public TrainService() throws IOException{
          this.trains = loadTrains();
     }

     public List<Train> loadTrains() throws IOException{
          File trains = new File(TRAIN_PATH);
          return objectMapper.readValue(trains, new TypeReference<List<Train>>() {});
     }

     // getters
     public List<Train> searchTrain(String source, String destination){
          return trains.stream().filter(train-> validTrain(train, source, destination)).collect(Collectors.toList());
     }

     private boolean validTrain(Train train, String source, String destination){
          List<String> stationOrder = train.getStations();
          int sourceIndex = stationOrder.indexOf(source.toLowerCase());
          int destinationIndex = stationOrder.indexOf(destination.toLowerCase());
          return sourceIndex != -1 && destinationIndex != -1 && sourceIndex < destinationIndex;
     }
}
