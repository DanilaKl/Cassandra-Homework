package homework.shop.controller;

import homework.shop.dto.GetCardDto;
import homework.shop.dto.GetCardPreviewDto;
import homework.shop.dto.PutCardDto;
import homework.shop.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/get/card")
    @ResponseBody
    public GetCardDto getCard(@RequestParam("id") UUID id) {
        return cardService.getCard(id);
    }

    @GetMapping("/list/cards")
    @ResponseBody
    public List<GetCardPreviewDto> listCards() {
        return cardService.getAllCards();
    }

    @PostMapping("/put/card")
    @ResponseBody
    public Map<String, String> putCard(@RequestBody PutCardDto putCardDto) {
        UUID id = cardService.putCard(putCardDto);
        return Map.of("id", id.toString());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error_message", ex.getMessage()));
    }
}
