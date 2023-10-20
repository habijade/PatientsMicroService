package com.nnk.springboot.services;


import com.nnk.springboot.domain.CurvePoint;

import java.util.List;
import java.util.Optional;

public interface CurvePointService {
    CurvePoint createCurvePoint(CurvePoint curvePoint);

    Optional<CurvePoint> getCurvePointById(Integer id);

    List<CurvePoint> getAllCurvePoint();

    CurvePoint updateCurvePoint(Integer id);

    void deleteCurvePoint(Integer id);

    CurvePoint addNewCurvePoint(Integer id, CurvePoint updateCurvePoint);
}
