package com.kevin.todo.orderandinventorymanagementsystem.entity;

import com.mongodb.lang.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@NoArgsConstructor
public class UserEntity {
    @Id
    private ObjectId userId;
    @Indexed(unique = true)
    @NonNull
    private String userName;
    @NonNull
    private String password;
    private String email;
    private List<String> roles;
//    @DBRef // this annotation will create the reference of the JournalEntries table(class) in to users table(table)
    private List<ObjectId> orders = new ArrayList<>();


}
