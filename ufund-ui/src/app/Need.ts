export interface Need {
  id: number;
  name: string;
  type: string;
  quantity: number;
  price: number;
  userBaskets: { [key: string]: number };
}
