with Ada.Numerics.Discrete_Random;
with Ada.Text_IO; use Ada.Text_IO;

procedure main2 is
   type randNum is new Integer range -100000..100000;
   type numsArr is array (0..999) of Integer;
   
   arr: numsArr;
   
   package Rand_Num is new ada.numerics.discrete_random(randNum);
   gen_num: Rand_Num.Generator;
   
   procedure Init_Arr is 
   begin
      Rand_Num.reset(gen_num);
      for i in 0..999 loop
         arr(i) := Integer(Rand_Num.Random(gen_num));
      end loop;
   end Init_Arr;
   
   function part_min(start_index, finish_index : Integer) return Integer is
      min: Integer := Integer'last;
   begin
      for i in start_index..finish_index loop
         if arr(i) < min then
            min := arr(i);
         end if;
      end loop;
      return min;
   end part_min;
   
   protected min_store is 
      procedure set_min(min: Integer);
      entry get_min(min : out Integer);
   private
      global_min : Integer := Integer'Last;
   end min_store;
   
   protected body min_store is
      procedure set_min(min: Integer) is
      begin
         if min < global_min then
            global_min := min;
         end if;
      end set_min;
      
      entry get_min(min : out Integer) when True is
      begin
         min := global_min;
      end get_min;
   end min_store;
   
   task type min_thread is
      entry start(start_index, finish_index : Integer);
   end min_thread;
   
   task body min_thread is
      min: Integer;
   begin
      accept start(start_index, finish_index: Integer) do
         min := part_min(start_index => start_index,
                         finish_index => finish_index);
         min_store.set_min(min);
      end start;
   end min_thread;
   
   function parallel_min return Integer is
      min : Integer := Integer'Last;
      threads: array(0..9) of min_thread;
   begin
      for i in 0..9 loop
         threads(i).start(i*100, i*100+99);
      end loop;
      min_store.get_min(min);
      return min;
   end parallel_min;
begin
   Init_Arr;
   Put_Line("One thread min: " & Integer'Image(part_min(start_index => 0, finish_index => 999)));
   Put_Line("Paralel min: " & Integer'Image(parallel_min));
end main2;
