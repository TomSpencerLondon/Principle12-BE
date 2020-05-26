package com.codurance.principle12.repositories;

//@Repository
//public class InMemoryBoardRepository implements BoardRepository {
//
//  private final Board board;
//
//  public InMemoryBoardRepository() {
//    this.board = new Board(List.of(
//        new Column(0, "Start", new ArrayList<>()),
//        new Column(1, "Stop", new ArrayList<>()),
//        new Column(2, "Continue", new ArrayList<>())));
//  }
//
//  @Override
//  public Board getBoard() {
//    return board;
//  }
//
////  @Override
////  public void addCard(Card card) {
////    Optional<Column> columnOptional = board.getColumns().stream()
////        .filter(column -> card.getColumnId() == column.getId())
////        .findAny();
////    if (!columnOptional.isPresent()) {
////      throw new ColumnNotFoundException("Column Id is not valid");
////    }
////    columnOptional.get().getCards().add(card);
////  }
//
//}
