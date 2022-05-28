package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the user type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "users")
public final class user implements Model {
  public static final QueryField ID = field("user", "id");
  public static final QueryField USER_NAME = field("user", "userName");
  public static final QueryField TEAM_USER = field("user", "TeamUser");
  public static final QueryField TEAM_LIST_USER_ID = field("user", "teamListUserId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String userName;
  private final @ModelField(targetType="String") String TeamUser;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  private final @ModelField(targetType="ID") String teamListUserId;
  public String getId() {
      return id;
  }
  
  public String getUserName() {
      return userName;
  }
  
  public String getTeamUser() {
      return TeamUser;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  public String getTeamListUserId() {
      return teamListUserId;
  }
  
  private user(String id, String userName, String TeamUser, String teamListUserId) {
    this.id = id;
    this.userName = userName;
    this.TeamUser = TeamUser;
    this.teamListUserId = teamListUserId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      user user = (user) obj;
      return ObjectsCompat.equals(getId(), user.getId()) &&
              ObjectsCompat.equals(getUserName(), user.getUserName()) &&
              ObjectsCompat.equals(getTeamUser(), user.getTeamUser()) &&
              ObjectsCompat.equals(getCreatedAt(), user.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), user.getUpdatedAt()) &&
              ObjectsCompat.equals(getTeamListUserId(), user.getTeamListUserId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUserName())
      .append(getTeamUser())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getTeamListUserId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("user {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("userName=" + String.valueOf(getUserName()) + ", ")
      .append("TeamUser=" + String.valueOf(getTeamUser()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("teamListUserId=" + String.valueOf(getTeamListUserId()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static user justId(String id) {
    return new user(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      userName,
      TeamUser,
      teamListUserId);
  }
  public interface BuildStep {
    user build();
    BuildStep id(String id);
    BuildStep userName(String userName);
    BuildStep teamUser(String teamUser);
    BuildStep teamListUserId(String teamListUserId);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String userName;
    private String TeamUser;
    private String teamListUserId;
    @Override
     public user build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new user(
          id,
          userName,
          TeamUser,
          teamListUserId);
    }
    
    @Override
     public BuildStep userName(String userName) {
        this.userName = userName;
        return this;
    }
    
    @Override
     public BuildStep teamUser(String teamUser) {
        this.TeamUser = teamUser;
        return this;
    }
    
    @Override
     public BuildStep teamListUserId(String teamListUserId) {
        this.teamListUserId = teamListUserId;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String userName, String teamUser, String teamListUserId) {
      super.id(id);
      super.userName(userName)
        .teamUser(teamUser)
        .teamListUserId(teamListUserId);
    }
    
    @Override
     public CopyOfBuilder userName(String userName) {
      return (CopyOfBuilder) super.userName(userName);
    }
    
    @Override
     public CopyOfBuilder teamUser(String teamUser) {
      return (CopyOfBuilder) super.teamUser(teamUser);
    }
    
    @Override
     public CopyOfBuilder teamListUserId(String teamListUserId) {
      return (CopyOfBuilder) super.teamListUserId(teamListUserId);
    }
  }
  
}
