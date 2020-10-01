package com.verminsnest.misc.gui.dialogs;

import com.badlogic.gdx.graphics.Texture;
import com.verminsnest.core.management.Indentifiers;
import com.verminsnest.core.management.data.RuntimeData;
import com.verminsnest.misc.gui.FontText;

public class TradeDialog extends ChoiceDialog {
	protected FontText price;
	protected Texture foodIcon;
	public TradeDialog(String iconPath, int[] position, int price) {
		super("Gameplay_Dialog_Accept","Gameplay_Dialog_Cancel","Gameplay_Dialog_Description_Item_Trade", iconPath, position, Indentifiers.ITEMDIALOG);
		description.getPos()[1] += 25;
		this.price = new FontText("x "+price,50, false);
		foodIcon = RuntimeData.getInstance().getTexture("textures/items/Food.png");
		this.price.setPos(new int[]{description.getPos()[0]+description.getBounds()[0]/2-this.price.getBounds()[0]/2,(int) (description.getPos()[1]-this.price.getBounds()[0]*1.25)});
	}
	
	@Override
	public void render(float delta){
		super.render(delta);
		price.draw(RuntimeData.getInstance().getGame().getBatch());
		RuntimeData.getInstance().getGame().getBatch().draw(foodIcon, price.getPos()[0]-foodIcon.getWidth()-5, (float) (price.getPos()[1]-price.getBounds()[1]));
	}
}
