package org.example.Column;

import org.example.Exception.ColumnValidateException;
import org.example.Validators.ColumnValidator;
import org.example.Validators.TaskValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;



import static org.mockito.Mockito.*;

public class ColumnServiceImplTest {

    @Mock
    private ColumnRepository columnRepository;

    @Mock
    private ColumnValidator columnValidator;

    @InjectMocks
    private ColumnServiceImpl columnService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateColumn() {
        Long boardId = 1L;
        String columnName = "TestColumn";

        columnService.createColumn(boardId, columnName);

        verify(columnRepository, times(1))
                .createColumn(eq(boardId), eq(columnName));
    }

    @Test
    public void testUpdateColumnName() {
        int columnId = 1;
        String updateName = "UpdatedColumn";

        columnService.updateColumnName(columnId, updateName);

        verify(columnValidator, times(1))
                .columnValidateName(eq(updateName));
        verify(columnRepository, times(1))
                .updateColumnName(eq(columnId), eq(updateName));
    }
}