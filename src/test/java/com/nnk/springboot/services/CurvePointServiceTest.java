package com.nnk.springboot.services;

import com.nnk.springboot.services.impl.CurvePointServiceImpl;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurvePointServiceTest {

    @InjectMocks
    private CurvePointServiceImpl curvePointServiceImpl;

    @Mock
    private CurvePointRepository curvePointRepository;


    @Captor
    private ArgumentCaptor<CurvePoint> CurvePointArgumentCaptor;

    private CurvePoint createCurvePoint(int id) {
        return new CurvePoint(1, 50.0);
    }

    @Test
    public void testNotAddNewCurvePoint() {
        Integer id = 1;
        CurvePoint updateCurvePoint = createCurvePoint(id);

        when(curvePointRepository.findById(id)).thenReturn(Optional.empty());

        Executable executable = () -> curvePointServiceImpl.addNewCurvePoint(id, updateCurvePoint);
        assertThrows(RuntimeException.class, executable, "CurvePoint not found with id " + id);
        verify(curvePointRepository).findById(id);
        verifyNoMoreInteractions(curvePointRepository);
    }
    
    @Test
    public void testGetCurvePointById() {
        Integer id = 1;
        CurvePoint expectedCurvePoint = createCurvePoint(id);
        when(curvePointRepository.findById(id)).thenReturn(Optional.of(expectedCurvePoint));
        Optional<CurvePoint> result = curvePointServiceImpl.getCurvePointById(id);
        verify(curvePointRepository).findById(id);
        assertTrue(result.isPresent());
        assertEquals(expectedCurvePoint, result.get());
    }

    @Test
    public void testAddNewCurvePoint() {
        Integer id = 1;
        CurvePoint updateCurvePoint = createCurvePoint(id);
        when(curvePointRepository.findById(id)).thenReturn(Optional.of(updateCurvePoint));

        CurvePoint result = curvePointServiceImpl.addNewCurvePoint(id, updateCurvePoint);
        verify(curvePointRepository).findById(1);
        verify(curvePointRepository).save(CurvePointArgumentCaptor.capture());
    }

    @Test
    public void testSaveCurvePoint() {
        CurvePoint curvePoint = createCurvePoint(1);

        curvePointServiceImpl.createCurvePoint(curvePoint);

        verify(curvePointRepository).save(CurvePointArgumentCaptor.capture());
        CurvePoint capturedCurvePoint = CurvePointArgumentCaptor.getValue();
        assertThat(capturedCurvePoint).isEqualTo(curvePoint);
    }

    @Test
    public void testListAllCurvePoint() {
        List<CurvePoint> expectedCurvePoint = new ArrayList<>();
        expectedCurvePoint.add(createCurvePoint(1));
        expectedCurvePoint.add(createCurvePoint(2));

        when(curvePointRepository.findAll()).thenReturn(expectedCurvePoint);

        List<CurvePoint> actualRating = curvePointServiceImpl.getAllCurvePoint();

        verify(curvePointRepository).findAll();
        assertThat(actualRating).isEqualTo(expectedCurvePoint);
    }

    @Test
    public void testUpdateCurvePoint() {

        CurvePoint expectedCurvePoint = createCurvePoint(1);
        Optional<CurvePoint> expectedOptional = Optional.of(expectedCurvePoint);

        when(curvePointRepository.findById(expectedCurvePoint.getCurveId())).thenReturn(expectedOptional);

        CurvePoint actualCurvePoint = curvePointServiceImpl.updateCurvePoint(expectedCurvePoint.getCurveId());

        verify(curvePointRepository).findById(expectedCurvePoint.getCurveId());
        assertThat(actualCurvePoint).isEqualTo(expectedCurvePoint);
    }

    @Test
    public void testDeleteCurvePoint() {
        Integer id = 1;
        CurvePoint curve = createCurvePoint(id);

        curvePointServiceImpl.deleteCurvePoint(id);

        verify(curvePointRepository, times(1)).deleteById(id);
    }
}
