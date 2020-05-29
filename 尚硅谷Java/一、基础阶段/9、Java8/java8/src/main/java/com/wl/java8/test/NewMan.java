package com.wl.java8.test;

import com.wl.java8.Godness;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

//注意：Optional 不能被序列化
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewMan {

	private Optional<Godness> godness = Optional.empty();

	private Godness god;

	public NewMan(Optional<Godness> godness) {
		this.godness = godness;
	}

}
