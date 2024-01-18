package org.example.Column;

import org.example.Task.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ColumnService {

    void createColumn(Long boardId,String columnName);

    void updateColumnName(int columnId, String updateName);
}
