package org.example.Column;

import org.example.Validators.ColumnValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColumnServiceImpl implements ColumnService {
    private ColumnRepository columnRepository;

    private ColumnValidator columnValidator;
    @Autowired
    public ColumnServiceImpl(ColumnRepository columnRepository, ColumnValidator columnValidator) {
        this.columnRepository = columnRepository;
        this.columnValidator = columnValidator;
    }


    @Override
    public void createColumn(Long boardId,String columnName) {
        columnRepository.createColumn(boardId,columnName);
    }

    @Override
    public void updateColumnName(int columnId, String updateName) {
        columnValidator.columnValidateName(updateName);
        columnRepository.updateColumnName(columnId, updateName);
    }
}
