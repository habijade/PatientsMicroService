package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CurvePointServiceImpl implements CurvePointService {
    @Autowired
    private CurvePointRepository curvePointRepository;

    @Override
    public CurvePoint createCurvePoint(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }

    @Override
    public Optional<CurvePoint> getCurvePointById(Integer id) {
        return curvePointRepository.findById(id);
    }

    @Override
    public List<CurvePoint> getAllCurvePoint() {
        return curvePointRepository.findAll();
    }

    @Override
    public CurvePoint addNewCurvePoint(Integer id, CurvePoint updatedCurvePoint) {
        Optional<CurvePoint> existingCurvePointOpt = curvePointRepository.findById(id);
        if (existingCurvePointOpt.isPresent()) {
            CurvePoint existingCurvePoint = existingCurvePointOpt.get();
            existingCurvePoint.setCurveId(updatedCurvePoint.getCurveId());
            existingCurvePoint.setValue(updatedCurvePoint.getValue());
            existingCurvePoint.setTerm(updatedCurvePoint.getTerm());
            return curvePointRepository.save(existingCurvePoint);
        } else {
            throw new RuntimeException("CurvePoint not found with id " + id);
        }
    }

    @Override
    public CurvePoint updateCurvePoint(Integer id) {
        return curvePointRepository.findById(id).get();
    }

    @Override
    public void deleteCurvePoint(Integer id) {
        curvePointRepository.deleteById(id);
    }
}
