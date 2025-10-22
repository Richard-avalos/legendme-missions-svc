package com.legendme.missions_svc.adapters.in.rest.controller;

import com.legendme.missions_svc.adapters.in.rest.dto.*;
import com.legendme.missions_svc.application.port.in.command.CreateMissionCommand;
import com.legendme.missions_svc.application.port.in.command.SearchMissionCommand;
import com.legendme.missions_svc.application.mapper.MissionMapper;
import com.legendme.missions_svc.application.port.in.*;
import com.legendme.missions_svc.domain.model.Mission;
import com.legendme.missions_svc.util.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
/**
 * Controlador REST para gestionar misiones.
 * Proporciona endpoints para crear, buscar, obtener detalles y actualizar el estado de las misiones.
 */
@RestController
@RequestMapping("/legendme/missions")
public class MissionController {

    private final CreateMissionUseCase createMissionUseCase;
    private final SearchMissionsUseCase searchMissionsUseCase;
    private final GetMissionDetailUseCase getMissionDetailUseCase;
    private final StartMissionUseCase startMissionUseCase;
    private final PauseMissionUseCase pauseMissionUseCase;
    private final CompleteMissionUseCase completeMissionUseCase;
    private final CancelMissionUseCase cancelMissionUseCase;
    private final JwtUtils jwtUtils;

    /**
     * Constructor para inyectar las dependencias necesarias.
     */
    public MissionController(
            CreateMissionUseCase createMissionUseCase,
            SearchMissionsUseCase searchMissionsUseCase,
            GetMissionDetailUseCase getMissionDetailUseCase,
            StartMissionUseCase startMissionUseCase,
            PauseMissionUseCase pauseMissionUseCase,
            CompleteMissionUseCase completeMissionUseCase,
            CancelMissionUseCase cancelMissionUseCase, JwtUtils jwtUtils
    ) {
        this.createMissionUseCase = createMissionUseCase;
        this.searchMissionsUseCase = searchMissionsUseCase;
        this.getMissionDetailUseCase = getMissionDetailUseCase;
        this.startMissionUseCase = startMissionUseCase;
        this.pauseMissionUseCase = pauseMissionUseCase;
        this.completeMissionUseCase = completeMissionUseCase;
        this.cancelMissionUseCase = cancelMissionUseCase;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Extrae el ID de usuario del encabezado de autorización.
     *
     * @param authHeader El encabezado de autorización que contiene el token JWT.
     * @return El UUID del usuario extraído del token.
     */
    private UUID getUserId(String authHeader) {
        return jwtUtils.extractUserIdFromAuthorizationHeader(authHeader);
    }

    /**
     * Construye una respuesta HTTP con el estado OK y el cuerpo de la misión.
     *
     * @param mission La misión a incluir en la respuesta.
     * @return Una respuesta HTTP con el estado OK y la misión en el cuerpo.
     */
    private ResponseEntity<?> ok(Mission mission) {
        return ResponseEntity.ok(MissionMapper.toResponse(mission));
    }

    /**
     * Construye una respuesta HTTP con el estado OK y el cuerpo de la lista de misiones.
     *
     * @param missions La lista de misiones a incluir en la respuesta.
     * @return Una respuesta HTTP con el estado OK y la lista de misiones en el cuerpo.
     */
    private ResponseEntity<?> ok(List<Mission> missions) {
        return ResponseEntity.ok(missions.stream().map(MissionMapper::toResponse).collect(Collectors.toList()));
    }


    /**
     * Maneja la creación de una nueva misión.
     * @param authHeader
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<?> createMission(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody CreateMissionRequest request
    ) {
        UUID userId = getUserId(authHeader);
        var cmd = new CreateMissionCommand(
                userId,
                request.categoryId(),
                request.title(),
                request.description(),
                request.dueAt(),
                request.difficulty(),
                request.streakGroup()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(MissionMapper.toResponse(createMissionUseCase.execute(cmd)));
    }

    /**
     * Maneja la búsqueda de misiones según los criterios especificados.
     *
     * @param authHeader El encabezado de autorización que contiene el token JWT.
     * @param request    La solicitud de búsqueda que contiene los filtros opcionales.
     * @return Una respuesta HTTP con la lista de misiones que coinciden con los criterios de búsqueda.
     */
    @PostMapping("/query")
    public ResponseEntity<?> searchMissions(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody(required = false) MissionSearchRequest request
    ) {
        UUID userId = getUserId(authHeader);
        var cmd = new SearchMissionCommand(
                Optional.ofNullable(request).map(MissionSearchRequest::status).orElse(null),
                Optional.ofNullable(request).map(MissionSearchRequest::categoryCode).orElse(null),
                Optional.ofNullable(request).map(MissionSearchRequest::from).orElse(null),
                Optional.ofNullable(request).map(MissionSearchRequest::to).orElse(null)
        );
        return ok(searchMissionsUseCase.execute(cmd, userId));
    }

    /**
     * Maneja la obtención de los detalles de una misión específica.
     *
     * @param authHeader El encabezado de autorización que contiene el token JWT.
     * @param id         El ID de la misión cuyos detalles se desean obtener.
     * @return Una respuesta HTTP con los detalles de la misión solicitada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getMissionDetail(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable UUID id
    ) {
        UUID userId = getUserId(authHeader);
        return ok(getMissionDetailUseCase.execute(id, userId));
    }

    /**
     * Maneja el inicio de una misión.
     *
     * @param authHeader El encabezado de autorización que contiene el token JWT.
     * @param id         El ID de la misión que se desea iniciar.
     * @return Una respuesta HTTP con la misión iniciada.
     */
    @PatchMapping("/{id}/start")
    public ResponseEntity<?> startMission(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable UUID id
    ) {
        UUID userId = getUserId(authHeader);
        return ok(startMissionUseCase.execute(id, userId));
    }

    /**
     * Maneja la pausa de una misión.
     *
     * @param authHeader   El encabezado de autorización que contiene el token JWT.
     * @param id           El ID de la misión que se desea pausar.
     * @param pauseRequest La solicitud de pausa que puede contener una nota opcional.
     * @return Una respuesta HTTP con la misión pausada.
     */
    @PatchMapping("/{id}/pause")
    public ResponseEntity<?> pauseMission(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable UUID id,
            @RequestBody(required = false) PauseRequest pauseRequest
    ) {
        UUID userId = getUserId(authHeader);
        String note = Optional.ofNullable(pauseRequest).map(PauseRequest::note).orElse(null);
        return ok(pauseMissionUseCase.execute(id, userId, note));
    }

    /**
     * Maneja la finalización de una misión.
     *
     * @param authHeader      El encabezado de autorización que contiene el token JWT.
     * @param id              El ID de la misión que se desea completar.
     * @param completeRequest La solicitud de finalización que puede contener una nota opcional.
     * @param idempotencyKey  La clave de idempotencia para evitar operaciones duplicadas.
     * @return Una respuesta HTTP con la misión completada o un error si falta la clave de idempotencia.
     */
    @PatchMapping("/{id}/complete")
    public ResponseEntity<?> completeMission(
            @RequestHeader("Authorization") String authHeader,
            @RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey,
            @PathVariable UUID id,
            @RequestBody(required = false) CompleteRequest completeRequest
    ) {
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_REQUIRED)
                    .body(new com.legendme.missions_svc.adapters.in.rest.dto.ErrorResponse(
                            "PRECONDITION_REQUIRED",
                            "Missing Idempotency-Key",
                            "Header required"
                    ));
        }
        UUID userId = getUserId(authHeader);
        String note = Optional.ofNullable(completeRequest).map(CompleteRequest::note).orElse(null);
        return ok(completeMissionUseCase.execute(id, userId, idempotencyKey, note));
    }

    /**
     * Maneja la cancelación de una misión.
     *
     * @param authHeader    El encabezado de autorización que contiene el token JWT.
     * @param id            El ID de la misión que se desea cancelar.
     * @param cancelRequest La solicitud de cancelación que puede contener una razón opcional.
     * @return Una respuesta HTTP con la misión cancelada.
     */
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<?> cancelMission(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable UUID id,
            @RequestBody(required = false) CancelRequest cancelRequest
    ) {
        UUID userId = getUserId(authHeader);
        String reason = Optional.ofNullable(cancelRequest).map(CancelRequest::reason).orElse(null);
        return ok(cancelMissionUseCase.execute(id, userId, reason));
    }

    /**Estas clases representan las solicitudes para pausar, completar y cancelar misiones. */
    public static record PauseRequest(String note) {}
    public static record CompleteRequest(String note) {}
    public static record CancelRequest(String reason) {}
}
