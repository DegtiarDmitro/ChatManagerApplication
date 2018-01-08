package com.manager.chat.translator;

import com.manager.chat.entity.BaseEntity;
import org.json.JSONObject;

public interface JSONTranslator<T extends BaseEntity> {

    T fromJSON(JSONObject obj);
    JSONObject toJSON(T entity);
}
