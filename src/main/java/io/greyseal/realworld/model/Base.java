package io.greyseal.realworld.model;

import io.vertx.core.json.JsonObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.Date;

public abstract class Base implements Serializable {
    private static final long serialVersionUID = -5457854685085504749L;

    private ObjectId _id;
    private ObjectId createdBy;
    private Date createdDate;
    private Date updatedDate;
    private ObjectId updatedBy;
    private Boolean isActive;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public ObjectId getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(ObjectId createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public ObjectId getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(ObjectId updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(_id.toHexString()).append(32).toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        final Base _base = (Base) obj;
        return new EqualsBuilder().append(this._id.toHexString(), _base._id.toHexString()).isEquals();
    }

    protected JsonObject toBaseJson(Base base, JsonObject json) {
        if (base.getCreatedBy() != null) {
            json.put("createdBy", new JsonObject().put("$oid", base.getCreatedBy().toHexString()));
        }
        if (base.getUpdatedBy() != null) {
            json.put("updatedBy", new JsonObject().put("$oid", base.getUpdatedBy().toHexString()));
        }
        if (base.get_id() != null) {
            json.put("_id", new JsonObject().put("$oid", base.get_id().toHexString()));
        }
        return json;
    }

    public void fromBaseJson(JsonObject json, Base obj) {
        final JsonObject createdBy = json.getJsonObject("createdBy");
        if (null != createdBy) {
            obj.setCreatedBy(new ObjectId(createdBy.getValue("$oid").toString()));
        }
        final JsonObject updatedBy = json.getJsonObject("updatedBy");
        if (null != updatedBy) {
            obj.setUpdatedBy(new ObjectId(updatedBy.getValue("$oid").toString()));
        }
        if (json.getValue("_id") instanceof String) {
            this.set_id(new ObjectId(json.getValue("_id").toString()));
        }
    }
}