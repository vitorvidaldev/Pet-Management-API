import { Test, TestingModule } from "@nestjs/testing";
import { VaccinesController } from "./vaccines.controller";

describe("Data Controller", () => {
  let controller: VaccinesController;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [VaccinesController],
    }).compile();

    controller = module.get<VaccinesController>(VaccinesController);
  });

  it("should be defined", () => {
    expect(controller).toBeDefined();
  });
});
