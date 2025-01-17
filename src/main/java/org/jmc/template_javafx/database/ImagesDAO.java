package org.jmc.template_javafx.database;

import java.util.List;

public interface ImagesDAO<T> {
    List<byte[]> getImageByID(int id);
}
