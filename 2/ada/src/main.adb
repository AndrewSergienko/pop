with Ada.Numerics.Discrete_Random;
with Ada.Text_IO; use Ada.Text_IO;

procedure main is
   type randNum is new Integer range -100000..100000;
   
   arr_len: Integer := 1000;
   thread_nums: Integer;
   
   type numsArr is array (0..arr_len-1) of Integer;
   
   arr: numsArr;
   
   package Rand_Num is new ada.numerics.discrete_random(randNum);
   gen_num: Rand_Num.Generator;
   
   procedure Init_Arr is 
   begin
      Rand_Num.reset(gen_num);
      for i in 0..arr_len-1 loop
         arr(i) := Integer(Rand_Num.Random(gen_num));
      end loop;
   end Init_Arr;
   
   procedure part_min(start_index, finish_index: Integer; min_value, min_index : in out Integer) is
   begin
      for i in start_index..finish_index loop
         if arr(i) < min_value then
            min_value := arr(i);
            min_index := i;
         end if;
      end loop;
   end part_min;
   
   protected min_store is 
      procedure set_min(min_value, min_index: Integer);
      entry get_min(min_value, min_index: out Integer);
   private
      global_min_value : Integer := Integer'Last;
      global_min_index: Integer := 0;
   end min_store;
   
   protected body min_store is
      procedure set_min(min_value, min_index: Integer) is
      begin
         if min_value < global_min_value then
            global_min_value := min_value;
            global_min_index := min_index;
         end if;
      end set_min;
      
      entry get_min(min_value, min_index: out Integer) when True is
      begin
         min_value := global_min_value;
         min_index := global_min_index;
      end get_min;
   end min_store;
   
   task type min_thread is
      entry start(start_index, finish_index : Integer);
   end min_thread;
   
   task body min_thread is
      min_value: Integer := Integer'Last;
      min_index: Integer := 0;
   begin
      accept start(start_index, finish_index: Integer) do
         part_min(start_index => start_index, finish_index => finish_index,
                  min_value => min_value, min_index => min_index);
         min_store.set_min(min_value, min_index);
      end start;
   end min_thread;
   
   procedure parallel_min(threads_num: Integer) is
      min_value: Integer := Integer'Last;
      min_index: Integer := 0;
      part_len: Integer := arr_len / threads_num; 
      threads: array(0..threads_num-1) of min_thread;
   begin
      for i in 0..threads_num-2 loop
         threads(i).start(i*part_len, (i+1)*part_len-1);
      end loop;
      threads(threads_num-1).start((threads_num-1)*part_len, arr_len-1);
      min_store.get_min(min_value, min_index);
      Put_Line("Paralel min: arr(" & min_index'img &") = " & min_value'Img);
   end parallel_min;
   
   check_min_value: Integer := Integer'Last;
   check_min_index: Integer := 0;
begin
   Put_Line("number of threads:");
   thread_nums := Integer'Value(Get_Line);
   Init_Arr;
   part_min(0, arr_len-1, check_min_value, check_min_index);
   Put_Line("One thread min: arr("& check_min_index'img &") = " & check_min_value'Img);
   parallel_min(thread_nums);
end main;
