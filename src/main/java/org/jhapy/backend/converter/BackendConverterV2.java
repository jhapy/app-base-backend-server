package org.jhapy.backend.converter;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.jhapy.baseserver.converter.BaseConverterV2;
import org.jhapy.dto.domain.Comment;
import org.jhapy.dto.domain.reference.Country;
import org.jhapy.dto.domain.reference.IntermediateRegion;
import org.jhapy.dto.domain.reference.Region;
import org.jhapy.dto.domain.reference.SubRegion;
import org.jhapy.dto.domain.user.BaseUser;
import org.jhapy.dto.utils.LatLng;
import org.jhapy.dto.utils.MapBounds;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.context.annotation.Primary;

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

  public abstract Country convertToDto(org.jhapy.backend.domain.graphdb.reference.Country domain,
      @Context Map<String, Object> context);

  public abstract org.jhapy.backend.domain.graphdb.reference.Country convertToDomain(Country dto,
      @Context Map<String, Object> context);

  public abstract List<Country> convertToDtoCountries(
      Iterable<org.jhapy.backend.domain.graphdb.reference.Country> domains,
      @Context Map<String, Object> context);

  public abstract List<org.jhapy.backend.domain.graphdb.reference.Country> convertToDomainCountries(
      Iterable<Country> dtos, @Context Map<String, Object> context);

  public abstract Region convertToDto(org.jhapy.backend.domain.graphdb.reference.Region domain,
      @Context Map<String, Object> context);

  public abstract org.jhapy.backend.domain.graphdb.reference.Region convertToDomain(Region dto,
      @Context Map<String, Object> context);

  public abstract List<Region> convertToDtoRegions(
      Iterable<org.jhapy.backend.domain.graphdb.reference.Region> domains,
      @Context Map<String, Object> context);

  public abstract List<org.jhapy.backend.domain.graphdb.reference.Region> convertToDomainRegions(
      Iterable<Region> dtos, @Context Map<String, Object> context);

  public abstract IntermediateRegion convertToDto(
      org.jhapy.backend.domain.graphdb.reference.IntermediateRegion domain,
      @Context Map<String, Object> context);

  public abstract org.jhapy.backend.domain.graphdb.reference.IntermediateRegion convertToDomain(
      IntermediateRegion dto, @Context Map<String, Object> context);

  public abstract List<IntermediateRegion> convertToDtoIntermediateRegions(
      Iterable<org.jhapy.backend.domain.graphdb.reference.IntermediateRegion> domains,
      @Context Map<String, Object> context);

  public abstract List<org.jhapy.backend.domain.graphdb.reference.IntermediateRegion> convertToDomainIntermediateRegions(
      Iterable<IntermediateRegion> dtos, @Context Map<String, Object> context);

  public abstract SubRegion convertToDto(
      org.jhapy.backend.domain.graphdb.reference.SubRegion domain,
      @Context Map<String, Object> context);

  public abstract org.jhapy.backend.domain.graphdb.reference.SubRegion convertToDomain(
      SubRegion dto, @Context Map<String, Object> context);

  public abstract List<SubRegion> convertToDtoSubRegions(
      Iterable<org.jhapy.backend.domain.graphdb.reference.SubRegion> domains,
      @Context Map<String, Object> context);

  public abstract List<org.jhapy.backend.domain.graphdb.reference.SubRegion> convertToDomainSubRegions(
      Iterable<SubRegion> dtos, @Context Map<String, Object> context);

  @AfterMapping
  protected void afterConvert(Country dto,
      @MappingTarget org.jhapy.backend.domain.graphdb.reference.Country domain,
      @Context Map<String, Object> context) {
    String iso3Language = (String) context.get("iso3Language");
    if (StringUtils.isNotBlank(iso3Language)) {
      domain.getNames().setTranslation(iso3Language, dto.getName());
    }
  }

  @AfterMapping
  protected void afterConvert(org.jhapy.backend.domain.graphdb.reference.Country domain,
      @MappingTarget Country dto, @Context Map<String, Object> context) {
    String iso3Language = (String) context.get("iso3Language");
    if (StringUtils.isNotBlank(iso3Language)) {
      dto.setName(domain.getNames().getTranslation(iso3Language));
    }
  }

  @AfterMapping
  protected void afterConvert(IntermediateRegion dto,
      @MappingTarget org.jhapy.backend.domain.graphdb.reference.IntermediateRegion domain,
      @Context Map<String, Object> context) {
    String iso3Language = (String) context.get("iso3Language");
    if (StringUtils.isNotBlank(iso3Language)) {
      domain.getNames().setTranslation(iso3Language, dto.getName());
    }
  }

  @AfterMapping
  protected void afterConvert(org.jhapy.backend.domain.graphdb.reference.IntermediateRegion domain,
      @MappingTarget IntermediateRegion dto, @Context Map<String, Object> context) {
    String iso3Language = (String) context.get("iso3Language");
    if (StringUtils.isNotBlank(iso3Language)) {
      dto.setName(domain.getNames().getTranslation(iso3Language));
    }
  }

  @AfterMapping
  protected void afterConvert(Region dto,
      @MappingTarget org.jhapy.backend.domain.graphdb.reference.Region domain,
      @Context Map<String, Object> context) {

    String iso3Language = (String) context.get("iso3Language");
    if (StringUtils.isNotBlank(iso3Language)) {
      domain.getNames().setTranslation(iso3Language, dto.getName());
    }
  }

  @AfterMapping
  protected void afterConvert(org.jhapy.backend.domain.graphdb.reference.Region domain,
      @MappingTarget Region dto, @Context Map<String, Object> context) {
    String iso3Language = (String) context.get("iso3Language");
    if (StringUtils.isNotBlank(iso3Language)) {
      dto.setName(domain.getNames().getTranslation(iso3Language));
    }
  }

  @AfterMapping
  protected void afterConvert(SubRegion dto,
      @MappingTarget org.jhapy.backend.domain.graphdb.reference.SubRegion domain,
      @Context Map<String, Object> context) {
    String iso3Language = (String) context.get("iso3Language");
    if (StringUtils.isNotBlank(iso3Language)) {
      domain.getNames().setTranslation(iso3Language, dto.getName());
    }
  }

  @AfterMapping
  protected void afterConvert(org.jhapy.backend.domain.graphdb.reference.SubRegion domain,
      @MappingTarget SubRegion dto, @Context Map<String, Object> context) {
    String iso3Language = (String) context.get("iso3Language");
    if (StringUtils.isNotBlank(iso3Language)) {
      dto.setName(domain.getNames().getTranslation(iso3Language));
    }
  }
}
