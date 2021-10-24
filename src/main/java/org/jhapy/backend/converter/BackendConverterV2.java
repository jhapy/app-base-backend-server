package org.jhapy.backend.converter;

import org.apache.commons.lang3.StringUtils;
import org.jhapy.baseserver.converter.BaseConverterV2;
import org.jhapy.dto.domain.reference.CountryDTO;
import org.jhapy.dto.domain.reference.IntermediateRegionDTO;
import org.jhapy.dto.domain.reference.RegionDTO;
import org.jhapy.dto.domain.reference.SubRegionDTO;
import org.jhapy.dto.domain.user.BaseUser;
import org.jhapy.dto.utils.LatLng;
import org.jhapy.dto.utils.MapBounds;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Map;

/**
 * @author Alexandre Clavaud.
 * @version 1.0
 * @since 18/05/2021
 */
@Mapper(componentModel = "spring")
public abstract class BackendConverterV2 extends BaseConverterV2 {

  public abstract BaseUser convertToDto(org.jhapy.backend.domain.graphdb.user.BaseUser domain);

  public abstract org.jhapy.backend.domain.graphdb.user.BaseUser convertToDomain(BaseUser dto);

  public abstract List<BaseUser> convertToDtoBaseUsers(
      Iterable<org.jhapy.backend.domain.graphdb.user.BaseUser> domains);

  public abstract List<org.jhapy.backend.domain.graphdb.user.BaseUser> convertToDomainBaseUsers(
      Iterable<BaseUser> dtos);

  public abstract LatLng convertToDto(com.google.maps.model.LatLng domain);

  public abstract com.google.maps.model.LatLng convertToDomain(LatLng dto);

  public abstract List<LatLng> convertToDtoLatLngs(Iterable<com.google.maps.model.LatLng> domains);

  public abstract List<com.google.maps.model.LatLng> convertToDomainLatLngs(Iterable<LatLng> dtos);

  public abstract MapBounds convertToDto(org.jhapy.backend.domain.graphdb.utils.MapBounds domain);

  public abstract org.jhapy.backend.domain.graphdb.utils.MapBounds convertToDomain(MapBounds dto);

  public abstract List<MapBounds> convertToDtoMapBounds(
      Iterable<org.jhapy.backend.domain.graphdb.utils.MapBounds> domains);

  public abstract List<org.jhapy.backend.domain.graphdb.utils.MapBounds> convertToDomainMapBounds(
      Iterable<MapBounds> dtos);

  public abstract CountryDTO convertToDto(
      org.jhapy.backend.domain.graphdb.reference.Country domain,
      @Context Map<String, Object> context);

  public abstract org.jhapy.backend.domain.graphdb.reference.Country convertToDomain(
      CountryDTO dto, @Context Map<String, Object> context);

  public abstract List<CountryDTO> convertToDtoCountries(
      Iterable<org.jhapy.backend.domain.graphdb.reference.Country> domains,
      @Context Map<String, Object> context);

  public abstract List<org.jhapy.backend.domain.graphdb.reference.Country> convertToDomainCountries(
      Iterable<CountryDTO> dtos, @Context Map<String, Object> context);

  public abstract RegionDTO convertToDto(
      org.jhapy.backend.domain.graphdb.reference.Region domain,
      @Context Map<String, Object> context);

  public abstract org.jhapy.backend.domain.graphdb.reference.Region convertToDomain(
      RegionDTO dto, @Context Map<String, Object> context);

  public abstract List<RegionDTO> convertToDtoRegions(
      Iterable<org.jhapy.backend.domain.graphdb.reference.Region> domains,
      @Context Map<String, Object> context);

  public abstract List<org.jhapy.backend.domain.graphdb.reference.Region> convertToDomainRegions(
      Iterable<RegionDTO> dtos, @Context Map<String, Object> context);

  public abstract IntermediateRegionDTO convertToDto(
      org.jhapy.backend.domain.graphdb.reference.IntermediateRegion domain,
      @Context Map<String, Object> context);

  public abstract org.jhapy.backend.domain.graphdb.reference.IntermediateRegion convertToDomain(
      IntermediateRegionDTO dto, @Context Map<String, Object> context);

  public abstract List<IntermediateRegionDTO> convertToDtoIntermediateRegions(
      Iterable<org.jhapy.backend.domain.graphdb.reference.IntermediateRegion> domains,
      @Context Map<String, Object> context);

  public abstract List<org.jhapy.backend.domain.graphdb.reference.IntermediateRegion>
      convertToDomainIntermediateRegions(
          Iterable<IntermediateRegionDTO> dtos, @Context Map<String, Object> context);

  public abstract SubRegionDTO convertToDto(
      org.jhapy.backend.domain.graphdb.reference.SubRegion domain,
      @Context Map<String, Object> context);

  public abstract org.jhapy.backend.domain.graphdb.reference.SubRegion convertToDomain(
      SubRegionDTO dto, @Context Map<String, Object> context);

  public abstract List<SubRegionDTO> convertToDtoSubRegions(
      Iterable<org.jhapy.backend.domain.graphdb.reference.SubRegion> domains,
      @Context Map<String, Object> context);

  public abstract List<org.jhapy.backend.domain.graphdb.reference.SubRegion>
      convertToDomainSubRegions(Iterable<SubRegionDTO> dtos, @Context Map<String, Object> context);

  @AfterMapping
  protected void afterConvert(
      CountryDTO dto,
      @MappingTarget org.jhapy.backend.domain.graphdb.reference.Country domain,
      @Context Map<String, Object> context) {
    String iso3Language = (String) context.get("iso3Language");
    if (StringUtils.isNotBlank(iso3Language)) {
      domain.getNames().setTranslation(iso3Language, dto.getName());
    }
  }

  @AfterMapping
  protected void afterConvert(
      org.jhapy.backend.domain.graphdb.reference.Country domain,
      @MappingTarget CountryDTO dto,
      @Context Map<String, Object> context) {
    String iso3Language = (String) context.get("iso3Language");
    if (StringUtils.isNotBlank(iso3Language)) {
      dto.setName(domain.getNames().getTranslation(iso3Language));
    }
  }

  @AfterMapping
  protected void afterConvert(
      IntermediateRegionDTO dto,
      @MappingTarget org.jhapy.backend.domain.graphdb.reference.IntermediateRegion domain,
      @Context Map<String, Object> context) {
    String iso3Language = (String) context.get("iso3Language");
    if (StringUtils.isNotBlank(iso3Language)) {
      domain.getNames().setTranslation(iso3Language, dto.getName());
    }
  }

  @AfterMapping
  protected void afterConvert(
      org.jhapy.backend.domain.graphdb.reference.IntermediateRegion domain,
      @MappingTarget IntermediateRegionDTO dto,
      @Context Map<String, Object> context) {
    String iso3Language = (String) context.get("iso3Language");
    if (StringUtils.isNotBlank(iso3Language)) {
      dto.setName(domain.getNames().getTranslation(iso3Language));
    }
  }

  @AfterMapping
  protected void afterConvert(
      RegionDTO dto,
      @MappingTarget org.jhapy.backend.domain.graphdb.reference.Region domain,
      @Context Map<String, Object> context) {

    String iso3Language = (String) context.get("iso3Language");
    if (StringUtils.isNotBlank(iso3Language)) {
      domain.getNames().setTranslation(iso3Language, dto.getName());
    }
  }

  @AfterMapping
  protected void afterConvert(
      org.jhapy.backend.domain.graphdb.reference.Region domain,
      @MappingTarget RegionDTO dto,
      @Context Map<String, Object> context) {
    String iso3Language = (String) context.get("iso3Language");
    if (StringUtils.isNotBlank(iso3Language)) {
      dto.setName(domain.getNames().getTranslation(iso3Language));
    }
  }

  @AfterMapping
  protected void afterConvert(
      SubRegionDTO dto,
      @MappingTarget org.jhapy.backend.domain.graphdb.reference.SubRegion domain,
      @Context Map<String, Object> context) {
    String iso3Language = (String) context.get("iso3Language");
    if (StringUtils.isNotBlank(iso3Language)) {
      domain.getNames().setTranslation(iso3Language, dto.getName());
    }
  }

  @AfterMapping
  protected void afterConvert(
      org.jhapy.backend.domain.graphdb.reference.SubRegion domain,
      @MappingTarget SubRegionDTO dto,
      @Context Map<String, Object> context) {
    String iso3Language = (String) context.get("iso3Language");
    if (StringUtils.isNotBlank(iso3Language)) {
      dto.setName(domain.getNames().getTranslation(iso3Language));
    }
  }
}