package org.example.Column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColumnServiceImpl implements ColumnService {
    private ColumnRepository columnRepository;
    @Autowired
    public ColumnServiceImpl(ColumnRepository columnRepository) {
        this.columnRepository = columnRepository;
    }


    @Override
    public void createColumn(Long boardId,String columnName) {
        columnRepository.createColumn(boardId,columnName);
    }

    @Override
    public void updateColumnName(int columnId, String updateName) {
        columnRepository.updateColumnName(columnId, updateName);
    }
}
