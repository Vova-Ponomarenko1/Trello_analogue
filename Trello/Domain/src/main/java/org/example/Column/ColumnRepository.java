package org.example.Column;

import org.example.Exception.BoardNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColumnRepository {

    void createColumn(Long boardId, String columnName) throws BoardNotFoundException;

    void updateColumnName(int columnId, String updateName);

    void deleteColumnById(Long columnId);

    void synchronizeColumnPositions(List<ColumnPositionDTO> positions);

}
